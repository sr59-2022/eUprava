import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FakultetService, IspitOpcijaDto, PrijavaIspitaDto } from '../../services/fakultet.service';

@Component({
  selector: 'app-prijava-ispita',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prijava-ispita.component.html',
  styleUrls: ['./prijava-ispita.component.css']
})
export class PrijavaIspitaComponent implements OnInit {


  ispiti: IspitOpcijaDto[] = [];
  selectedIspitId: number | null = null;


  mojePrijave: PrijavaIspitaDto[] = [];
  selectedPrijavaIspitId: number | null = null;

  loading = false;
  successMsg = '';
  errorMsg = '';

  constructor(private fakultetService: FakultetService) {}

  ngOnInit(): void {
    this.ucitajSve();
  }

  ucitajSve(): void {
    this.ucitajDostupneIspite();
    this.ucitajMojePrijave();
  }

  ucitajDostupneIspite(): void {
    this.reset();
    this.loading = true;

    this.fakultetService.dostupniIspiti().subscribe({
      next: (data) => {
        this.ispiti = data ?? [];
        this.selectedIspitId = this.ispiti.length ? this.ispiti[0].id : null;
        this.loading = false;
      },
      error: (err) => {
        this.errorMsg = this.extractError(err) || 'Ne mogu da učitam listu dostupnih ispita.';
        this.loading = false;
      }
    });
  }

  ucitajMojePrijave(): void {

    this.fakultetService.mojePrijave().subscribe({
      next: (data) => {
        // možeš ostaviti sve statuse ili filtrirati samo PRIJAVLJEN
        this.mojePrijave = (data ?? []).filter(p => p.status === 'PRIJAVLJEN');
        this.selectedPrijavaIspitId = this.mojePrijave.length ? this.mojePrijave[0].ispitId : null;
      },
      error: (err) => {
        this.errorMsg = this.extractError(err) || 'Ne mogu da učitam moje prijave.';
      }
    });
  }

  prijavi(): void {
    this.reset();

    if (this.selectedIspitId == null) {
      this.errorMsg = 'Izaberi ispit iz liste dostupnih.';
      return;
    }

    this.loading = true;
    this.fakultetService.prijaviIspit(this.selectedIspitId).subscribe({
      next: () => {
        this.successMsg = 'Uspješno prijavljen ispit.';
        this.loading = false;


        this.ucitajDostupneIspite();
        this.ucitajMojePrijave();
      },
      error: (err) => {
        this.errorMsg = this.extractError(err) || 'Greška pri prijavi.';
        this.loading = false;
      }
    });
  }


  otkaziPrijavuIzListe(): void {
    this.reset();

    if (this.selectedPrijavaIspitId == null) {
      this.errorMsg = 'Izaberi prijavljeni ispit iz liste "Moje prijave".';
      return;
    }

    this.loading = true;
    this.fakultetService.otkaziPrijavu(this.selectedPrijavaIspitId).subscribe({
      next: () => {
        this.successMsg = 'Prijava otkazana.';
        this.loading = false;


        this.ucitajDostupneIspite();
        this.ucitajMojePrijave();
      },
      error: (err) => {
        this.errorMsg = this.extractError(err) || 'Greška pri otkazivanju.';
        this.loading = false;
      }
    });
  }

  private reset(): void {
    this.successMsg = '';
    this.errorMsg = '';
  }

  private extractError(err: any): string | null {
    if (err?.error) {
      if (typeof err.error === 'string') return err.error;
      if (typeof err.error?.message === 'string') return err.error.message;
    }
    return null;
  }
}
