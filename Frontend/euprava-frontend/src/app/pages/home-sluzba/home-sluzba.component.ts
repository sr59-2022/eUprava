import { Component, OnInit } from '@angular/core';
import { OglasService } from '../../services/oglas.service';
import {Oglas, TipOglasa} from '../../model/oglas.model';
import {DatePipe} from '@angular/common';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {GradjaninService} from '../../services/gradjanin.service';
import {Gradjanin} from '../../model/gradjanin.model';

@Component({
  selector: 'app-home-sluzba',
  templateUrl: './home-sluzba.component.html',
  standalone: true,
  imports: [
    DatePipe, CommonModule, FormsModule
  ],
  styleUrls: ['./home-sluzba.component.css']
})
export class HomeSluzbaComponent implements OnInit {
  oglasi: Oglas[] = [];
  gradjanin?: Gradjanin;

  nazivPozicije = '';
  tipOglasa?: TipOglasa;
  tipoviOglasa = Object.values(TipOglasa);

  constructor(private oglasService: OglasService, private authService: AuthService,private gradjaninService: GradjaninService) { }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.gradjaninService.getMe().subscribe({
        next: (g) => {
          this.gradjanin = g;
          console.log('Gradjanin kreiran / učitan:', g);
        },
        error: (err) => console.error('Greška prilikom kreiranja gradjanina:', err)
      });

      this.getAllOglasi();
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
}
