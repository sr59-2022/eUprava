import { Component, OnInit } from '@angular/core';
import { OglasService } from '../../services/oglas.service';
import {Oglas, TipOglasa} from '../../model/oglas.model';
import {DatePipe} from '@angular/common';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';

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

  nazivPozicije = '';
  tipOglasa?: TipOglasa;
  tipoviOglasa = Object.values(TipOglasa);

  constructor(private oglasService: OglasService) { }

  ngOnInit(): void {
    this.getAllOglasi();
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
