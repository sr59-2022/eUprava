import { Component, OnInit } from '@angular/core';
import { OglasService } from '../../services/oglas.service';
import { Oglas } from '../../model/oglas.model';
import {DatePipe} from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home-sluzba',
  templateUrl: './home-sluzba.component.html',
  standalone: true,
  imports: [
    DatePipe, CommonModule
  ],
  styleUrls: ['./home-sluzba.component.css']
})
export class HomeSluzbaComponent implements OnInit {
  oglasi: Oglas[] = [];

  constructor(private oglasService: OglasService) { }

  ngOnInit(): void {
    this.getAllOglasi();
  }

  getAllOglasi() {
    this.oglasService.getAllOglasi().subscribe(oglasi => {
      this.oglasi = oglasi;
    });
  }
}
