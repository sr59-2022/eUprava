import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FakultetService, DiplomiraniPoGodiniDto } from '../../services/fakultet.service';

@Component({
  selector: 'app-sluzba',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sluzba.component.html',
  styleUrl: './sluzba.component.css'
})
export class SluzbaComponent implements OnInit {

  diplomiraniPoGodini: DiplomiraniPoGodiniDto[] = [];
  loading = false;
  error: string | null = null;

  constructor(private fakultetService: FakultetService) {}

  ngOnInit(): void {
    this.ucitajIzvestaj();
  }

  ucitajIzvestaj() {
    this.loading = true;
    this.error = null;

    this.fakultetService.getDiplomiraniPoGodini().subscribe({
      next: (data) => {
        this.diplomiraniPoGodini = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri učitavanju izveštaja.';
        this.loading = false;
      }
    });
  }
}
