import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FakultetService, OcenaPregledDto, OceneFilter } from '../../services/fakultet.service';

@Component({
  selector: 'app-fakultet',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fakultet.component.html',
  styleUrl: './fakultet.component.css'
})
export class FakultetComponent implements OnInit {
  ocene: OcenaPregledDto[] = [];
  loading = false;
  error: string | null = null;

  // filteri (bind na HTML)
  filter: OceneFilter = {
    ocena: null,
    ocenaMin: null,
    ocenaMax: null,
    polozio: null,
    predmet: null
  };

  constructor(private fakultetService: FakultetService) {}

  ngOnInit(): void {
    this.ucitajOcene();
  }

  ucitajOcene() {
    this.loading = true;
    this.error = null;

    this.fakultetService.mojeOcene().subscribe({
      next: (data) => {
        this.ocene = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri učitavanju ocena.';
        this.loading = false;
      }
    });
  }

  pretrazi() {

    if (this.filter.ocenaMin != null && this.filter.ocenaMax != null && this.filter.ocenaMin > this.filter.ocenaMax) {
      const tmp = this.filter.ocenaMin;
      this.filter.ocenaMin = this.filter.ocenaMax;
      this.filter.ocenaMax = tmp;
    }

    this.loading = true;
    this.error = null;

    this.fakultetService.mojeOcene(this.filter).subscribe({
      next: (data) => {
        this.ocene = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri pretrazi ocena.';
        this.loading = false;
      }
    });
  }

  resetujFiltere() {
    this.filter = {
      ocena: null,
      ocenaMin: null,
      ocenaMax: null,
      polozio: null,
      predmet: null
    };
    this.ucitajOcene();
  }
}
