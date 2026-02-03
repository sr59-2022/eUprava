import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FakultetService, IspitOpcijaDto } from '../../services/fakultet.service';
import { RouterModule } from '@angular/router';



type PrijavaRow = {
  studentId: number;
  brojIndeksa: string;
  ime: string;
  prezime: string;
  status: string;
  ocena: number | null;
};

@Component({
  selector: 'app-profesor-ocene',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './profesor-ocene.component.html',
  styleUrls: ['./profesor-ocene.component.css']
})
export class ProfesorOceneComponent {
  ispiti: IspitOpcijaDto[] = [];
  selectedIspitId: number | null = null;

  prijave: PrijavaRow[] = [];

  loading = false;
  loaded = false;
  msg = '';

  constructor(private service: FakultetService) {}

  ngOnInit() {
    this.ucitajIspite();
  }

  ucitajIspite() {
    this.loading = true;
    this.msg = '';

    this.service.listaIspitaZaProfesora().subscribe({
      next: (res: any[]) => {
        // ako backend vrati IspitOpcijaDto, možeš i direktno castovati
        this.ispiti = res as IspitOpcijaDto[];
        this.loading = false;
      },
      error: () => {
        this.msg = 'Greška pri učitavanju ispita.';
        this.loading = false;
      }
    });
  }


  onIspitChange() {
    this.loaded = false;
    this.prijave = [];
    this.msg = '';
  }

  ucitajPrijave() {
    if (!this.selectedIspitId) return;

    this.loading = true;
    this.loaded = false;
    this.msg = '';

    this.service.prijaveZaIspit(this.selectedIspitId).subscribe({
      next: (res: any[]) => {
        // očekuje: { studentId, brojIndeksa, ime, prezime, status, ocena }
        this.prijave = (res ?? []).map((p: any) => ({
          studentId: p.studentId,
          brojIndeksa: p.brojIndeksa,
          ime: p.ime,
          prezime: p.prezime,
          status: p.status,
          ocena: p.ocena ?? null,
        }));

        this.loaded = true;
        this.loading = false;
      },
      error: () => {
        this.msg = 'Greška pri učitavanju prijava.';
        this.loaded = true;
        this.loading = false;
      }
    });
  }

  sacuvajOcenu(p: PrijavaRow) {
    if (!this.selectedIspitId || p.ocena == null) return;

    this.loading = true;
    this.msg = '';

    this.service.upisiOcenu(p.studentId, this.selectedIspitId, p.ocena).subscribe({
      next: () => {
        this.msg = 'Ocena sačuvana.';
        this.loading = false;


      },
      error: () => {
        this.msg = 'Greška pri upisu ocene.';
        this.loading = false;
      }
    });
  }
}
