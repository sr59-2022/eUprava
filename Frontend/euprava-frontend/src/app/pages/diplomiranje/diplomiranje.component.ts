import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FakultetService, DiplomiranjeStatusDto } from '../../services/fakultet.service';

@Component({
  selector: 'app-diplomiranje',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './diplomiranje.component.html',
  styleUrl: './diplomiranje.component.css'
})
export class DiplomiranjeComponent implements OnInit {
  loading = false;
  error: string | null = null;
  data: DiplomiranjeStatusDto | null = null;

  constructor(private fakultetService: FakultetService) {}

  ngOnInit(): void {
    this.ucitaj();
  }

  ucitaj() {
    this.loading = true;
    this.error = null;

    this.fakultetService.getDiplomiranjeStatus().subscribe({
      next: (res) => {
        this.data = res;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri učitavanju statusa diplomiranja.';
        this.loading = false;
      }
    });
  }

  get ispunjava(): boolean {
    return this.data?.status === 'ISPUNJAVA';
  }

  get espbOk(): boolean {
    if (!this.data) return false;
    return (this.data.ukupnoEspb ?? 0) >= (this.data.potrebnoEspb ?? 240);
  }
}
