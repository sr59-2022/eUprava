import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FakultetService, OcenaPregledDto } from '../../services/fakultet.service';

@Component({
  selector: 'app-fakultet',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './fakultet.component.html',
  styleUrl: './fakultet.component.css'
})
export class FakultetComponent implements OnInit {
  ocene: OcenaPregledDto[] = [];
  loading = false;
  error: string | null = null;

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
}
