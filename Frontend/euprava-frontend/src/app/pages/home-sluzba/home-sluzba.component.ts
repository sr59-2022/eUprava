import { Component, OnInit } from '@angular/core';
import { OglasService } from '../../services/oglas.service';
import {Oglas, TipOglasa} from '../../model/oglas.model';
import {DatePipe} from '@angular/common';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {GradjaninService} from '../../services/gradjanin.service';
import {Gradjanin} from '../../model/gradjanin.model';
import {DodajOglasComponent} from '../dodaj-oglas/dodaj-oglas.component';
import {PrijavaService} from '../../services/prijava.service';
import {PrikazPrijave} from '../../model/prijava.model';
import {ObavestenjeService} from '../../services/obavestenje.service';
import {ObavestenjaComponent} from '../obavestenja/obavestenja.component';
import {RouterModule} from '@angular/router';
import {ObavestenjeDTO} from '../../model/obavestenje.model';

@Component({
  selector: 'app-home-sluzba',
  templateUrl: './home-sluzba.component.html',
  standalone: true,
  imports: [
    DatePipe, CommonModule, FormsModule, DodajOglasComponent, RouterModule, ObavestenjaComponent
  ],
  styleUrls: ['./home-sluzba.component.css']
})
export class HomeSluzbaComponent implements OnInit {
  oglasi: Oglas[] = [];
  gradjanin?: Gradjanin;

  nazivPozicije = '';
  tipOglasa?: TipOglasa;
  tipoviOglasa = Object.values(TipOglasa);
  showDodajForm: boolean = false;
  preporuke: Oglas[] = [];
  prijavljeniOglasi = new Map<number, PrikazPrijave>();
  getBrojNeprocitanih: number = 0;
  showObavestenjeModal: boolean = false;
  obavestenja: ObavestenjeDTO[] = [];


  constructor(private oglasService: OglasService, public authService: AuthService,private gradjaninService: GradjaninService, private prijavaService: PrijavaService, private obavestenjeService: ObavestenjeService) { }

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      return;
    }

    if (this.authService.isGradjanin()) {
      this.getAllOglasi();
      this.gradjaninService.getMe().subscribe({
        next: g => {
          this.gradjanin = g;

          this.oglasService.getPreporuke().subscribe({
            next: oglasi => this.preporuke = oglasi,
            error: err => console.error('Greška pri preporukama', err)
          });

          this.prijavaService.getMojePrijave().subscribe({
            next: prijave => {
              prijave.forEach(p => {
                if (p.oglasId) {
                  this.prijavljeniOglasi.set(p.oglasId, p);
                }
              });
            },
            error: err => console.error('Greška pri učitavanju prijava', err)
          });

        },
        error: err => console.error('Greška pri učitavanju profila', err)
      });
    } else if (this.authService.isPoslodavac()) {
      this.oglasService.getMojiOglasi().subscribe({
        next: oglasi => this.oglasi = oglasi,
        error: err => console.error('Greška pri učitavanju oglasa poslodavca', err)
      });

      this.obavestenjeService.getBrojNeprocitanih().subscribe({
        next: (broj) => {
          this.getBrojNeprocitanih = broj;

          if (broj > 0) {
            this.obavestenjeService.getObavestenja().subscribe({
              next: (data) => {
                console.log('Ucitana obavestenja', data);
                this.obavestenja = data;
                this.showObavestenjeModal = true;
              },
              error: err =>
                console.error('Greška pri učitavanju obaveštenja', err)
            });
          }
        },
        error: err =>
          console.error('Greška pri dobijanju broja nepročitanih obaveštenja', err),
      });
    }
  }


    getAllOglasi() {
    this.oglasService.getAllOglasi().subscribe(oglasi => {
      this.oglasi = oglasi;
    });
  }

  pretrazi(): void {
    this.oglasService.pretragaOglasa(this.nazivPozicije, this.tipOglasa)
      .subscribe(oglasi => this.oglasi = oglasi);
  }

  reset(): void {
    this.nazivPozicije = '';
    this.tipOglasa = undefined;
    this.getAllOglasi();
  }


  openDodajModal(): void {
    this.showDodajForm = true;
  }

  closeDodajModal(): void {
    this.showDodajForm = false;
  }

  osveziListu(): void {
    this.showDodajForm = false;
    this.getAllOglasi();
  }

  jePrijavljen(oglasId: number): boolean {
    return this.prijavljeniOglasi.has(oglasId);
  }

  statusPrijave(oglasId: number): string | null {
    return this.prijavljeniOglasi.get(oglasId)?.status ?? null;
  }

  prijaviSe(oglasId: number): void {
    this.prijavaService.prijaviSe(oglasId).subscribe({
      next: () => {
        // dodaj u mapu prijavljenih oglasa
        this.prijavljeniOglasi.set(oglasId, {
          oglasId,
          prijavaId: 0,
          nazivPozicije: '',
          nazivKompanije: '',
          datumPrijave: new Date().toISOString(),
          status: 'PODNETA'
        });
        alert('Uspešno ste se prijavili na oglas.');
      },
      error: err => {
        alert('Došlo je do greške pri prijavi.');
      }
    });
  }

  loadObavestenja(): void {
    this.obavestenjeService.getObavestenja().subscribe((data) => {
      this.obavestenja = data;
    });
  }


  onObavestenjeProcitano(id: number): void {
    this.obavestenja = this.obavestenja.filter(o => o.id !== id);

    if (this.getBrojNeprocitanih > 0) {
      this.getBrojNeprocitanih--;
    }
  }

  closeObavestenjeModal(): void {
    this.showObavestenjeModal = false;
  }

}
