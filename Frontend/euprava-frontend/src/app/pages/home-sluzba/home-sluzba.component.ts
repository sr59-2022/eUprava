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

@Component({
  selector: 'app-home-sluzba',
  templateUrl: './home-sluzba.component.html',
  standalone: true,
  imports: [
    DatePipe, CommonModule, FormsModule, DodajOglasComponent
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

  constructor(private oglasService: OglasService, public authService: AuthService,private gradjaninService: GradjaninService) { }

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      return;
    }

    this.getAllOglasi();

    if (this.authService.isGradjanin()) {
      this.gradjaninService.getMe().subscribe({
        next: g => {
          this.gradjanin = g;

          this.oglasService.getPreporuke().subscribe({
            next: oglasi => this.preporuke = oglasi,
            error: err => console.error('Greška pri preporukama', err)
          });
        },
        error: err => console.error('Greška pri učitavanju profila', err)
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

}
