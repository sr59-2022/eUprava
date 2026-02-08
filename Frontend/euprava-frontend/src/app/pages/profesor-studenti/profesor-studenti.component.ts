import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FakultetService, StudentRowDto } from '../../services/fakultet.service';

@Component({
  selector: 'app-profesor-studenti',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profesor-studenti.component.html',
  styleUrls: ['./profesor-studenti.component.css']
})
export class ProfesorStudentiComponent implements OnInit {

  q = '';
  studenti: StudentRowDto[] = [];

  page = 0;
  size = 20;
  totalPages = 0;

  loading = false;
  msg = '';
  isError = false;

  saving: Record<number, boolean> = {};
  savingStatus: Record<number, boolean> = {};   // 🔹 NOVO

  constructor(private service: FakultetService) {}

  ngOnInit(): void {
    this.ucitaj();
  }

  ucitaj() {
    this.loading = true;
    this.msg = '';
    this.isError = false;

    this.service.getStudenti(this.q, this.page, this.size).subscribe({
      next: (res) => {
        this.studenti = res?.content ?? [];
        this.totalPages = res?.totalPages ?? 0;
        this.loading = false;
      },
      error: (err) => {
        this.msg = this.extractMsg(err) || 'Greška pri učitavanju studenata.';
        this.isError = true;
        this.loading = false;
      }
    });
  }

  pretrazi() {
    this.page = 0;
    this.ucitaj();
  }

  prev() {
    if (this.page <= 0) return;
    this.page--;
    this.ucitaj();
  }

  next() {
    if (this.page + 1 >= this.totalPages) return;
    this.page++;
    this.ucitaj();
  }


  toggleZavrsni(s: StudentRowDto, value: boolean) {
    const old = s.zavrsniRadOdbranjen;
    s.zavrsniRadOdbranjen = value;

    this.saving[s.id] = true;
    this.msg = '';
    this.isError = false;

    this.service.postaviZavrsniRad(s.id, value).subscribe({
      next: () => {
        delete this.saving[s.id];
        this.ucitaj();
      },
      error: (err) => {
        s.zavrsniRadOdbranjen = old;
        delete this.saving[s.id];
        this.msg = this.extractMsg(err) || 'Greška pri snimanju statusa završnog rada.';
        this.isError = true;
      }
    });
  }


  promeniStatus(s: StudentRowDto, status: 'AKTIVAN' | 'DIPLOMIRAO') {

    const old = s.statusStudenta as 'AKTIVAN' | 'DIPLOMIRAO';
    s.statusStudenta = status;

    this.savingStatus[s.id] = true;
    this.msg = '';
    this.isError = false;

    this.service.postaviStatusStudenta(s.id, status).subscribe({
      next: () => {
        delete this.savingStatus[s.id];
        this.ucitaj();
      },
      error: (err) => {
        s.statusStudenta = old;
        delete this.savingStatus[s.id];
        this.msg = this.extractMsg(err) || 'Ne može se promijeniti status studenta.';
        this.isError = true;
      }
    });
  }

  private extractMsg(err: any): string | null {
    if (typeof err?.error === 'string') return err.error;
    if (typeof err?.error?.message === 'string') return err.error.message;
    return null;
  }
}
