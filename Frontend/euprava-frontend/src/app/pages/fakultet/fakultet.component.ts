import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FakultetService, PredmetIndeksDto, PredmetiFilter } from '../../services/fakultet.service';

@Component({
  selector: 'app-fakultet',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fakultet.component.html',
  styleUrl: './fakultet.component.css'
})
export class FakultetComponent implements OnInit {
  predmeti: PredmetIndeksDto[] = [];
  loading = false;
  error: string | null = null;

  filter: PredmetiFilter = {
    polozio: null,
    predmet: null
  };

  constructor(private fakultetService: FakultetService) {}

  ngOnInit(): void {
    this.ucitajPredmete();
  }

  ucitajPredmete() {
    this.loading = true;
    this.error = null;

    this.fakultetService.mojiPredmetiIndeks().subscribe({
      next: (data) => {
        this.predmeti = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri učitavanju predmeta.';
        this.loading = false;
      }
    });
  }

  pretrazi() {
    this.loading = true;
    this.error = null;

    this.fakultetService.mojiPredmetiIndeks(this.filter).subscribe({
      next: (data) => {
        this.predmeti = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri pretrazi predmeta.';
        this.loading = false;
      }
    });
  }

  resetujFiltere() {
    this.filter = {
      polozio: null,
      predmet: null
    };
    this.ucitajPredmete();
  }
}
