import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  FakultetService,
  IspitniRokDto,
  IspitniRokCreateDto,
  PredmetDto,
  IspitCreateDto
} from '../../services/fakultet.service';

@Component({
  selector: 'app-profesor-ispiti',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profesor-ispiti.component.html',
  styleUrls: ['./profesor-ispiti.component.css']
})
export class ProfesorIspitiComponent implements OnInit {

  rokovi: IspitniRokDto[] = [];
  predmeti: PredmetDto[] = [];


  rokForm: IspitniRokCreateDto = { naziv: '', pocetak: '', kraj: '' };

  ispitForm: IspitCreateDto = {
    predmetId: 0,
    rokId: 0,
    datumOdrzavanja: '',
    prijavaDo: '',
    sala: ''
  };

  loading = false;
  msg = '';
  isError = false;

  constructor(private service: FakultetService) {}

  ngOnInit(): void {
    this.ucitajSve();
  }

  ucitajSve() {
    this.loading = true;
    this.resetMsg();


    this.service.rokovi().subscribe({
      next: (r) => {
        this.rokovi = r ?? [];
        if (this.rokovi.length && this.ispitForm.rokId === 0) {
          this.ispitForm.rokId = this.rokovi[0].id;
        }
        this.loading = false;
      },
      error: (err) => {
        this.showError(err, 'Greška pri učitavanju rokova.');
        this.loading = false;
      }
    });


    this.service.predmeti().subscribe({
      next: (p) => {
        this.predmeti = p ?? [];
        if (this.predmeti.length && this.ispitForm.predmetId === 0) {
          this.ispitForm.predmetId = this.predmeti[0].id;
        }
      },
      error: (err) => {
        // ako endpoint još ne postoji
        this.showError(err, 'Greška pri učitavanju predmeta. Provjeri endpoint /api/predmeti.');
      }
    });
  }

  kreirajRok() {
    this.resetMsg();

    if (!this.rokForm.naziv?.trim() || !this.rokForm.pocetak || !this.rokForm.kraj) {
      this.msg = 'Popuni sva polja za rok.';
      this.isError = true;
      return;
    }

    this.loading = true;

    this.service.kreirajRok({
      naziv: this.rokForm.naziv.trim(),
      pocetak: this.rokForm.pocetak,
      kraj: this.rokForm.kraj
    }).subscribe({
      next: () => {
        this.msg = 'Rok uspješno kreiran.';
        this.isError = false;
        this.loading = false;


        this.rokForm = { naziv: '', pocetak: '', kraj: '' };

        this.osveziRokove();
      },
      error: (err) => {
        this.showError(err, 'Greška pri kreiranju roka.');
        this.loading = false;
      }
    });
  }

  kreirajIspit() {
    this.resetMsg();

    if (!this.ispitForm.predmetId || !this.ispitForm.rokId) {
      this.msg = 'Izaberi predmet i rok.';
      this.isError = true;
      return;
    }

    if (!this.ispitForm.datumOdrzavanja || !this.ispitForm.prijavaDo || !this.ispitForm.sala?.trim()) {
      this.msg = 'Popuni datum održavanja, prijava do i salu.';
      this.isError = true;
      return;
    }

    this.loading = true;

    this.service.kreirajIspit({
      predmetId: Number(this.ispitForm.predmetId),
      rokId: Number(this.ispitForm.rokId),
      datumOdrzavanja: this.ispitForm.datumOdrzavanja,
      prijavaDo: this.ispitForm.prijavaDo,
      sala: this.ispitForm.sala.trim()
    }).subscribe({
      next: () => {
        this.msg = 'Ispit uspješno kreiran.';
        this.isError = false;
        this.loading = false;


        this.ispitForm = {
          predmetId: this.predmeti.length ? this.predmeti[0].id : 0,
          rokId: this.rokovi.length ? this.rokovi[0].id : 0,
          datumOdrzavanja: '',
          prijavaDo: '',
          sala: ''
        };
      },
      error: (err) => {
        this.showError(err, 'Greška pri kreiranju ispita.');
        this.loading = false;
      }
    });
  }

  private osveziRokove() {
    this.service.rokovi().subscribe({
      next: (r) => {
        this.rokovi = r ?? [];
        if (this.rokovi.length && this.ispitForm.rokId === 0) {
          this.ispitForm.rokId = this.rokovi[0].id;
        }
      },
      error: () => { /* ignorisi */ }
    });
  }

  private resetMsg() {
    this.msg = '';
    this.isError = false;
  }

  private showError(err: any, fallback: string) {
    const backendMsg =
      typeof err?.error === 'string' ? err.error :
        typeof err?.error?.message === 'string' ? err.error.message :
          null;

    this.msg = backendMsg || fallback;
    this.isError = true;
  }
}
