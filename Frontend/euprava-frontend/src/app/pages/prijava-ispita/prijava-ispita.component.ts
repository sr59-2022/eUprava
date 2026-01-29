import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FakultetService } from '../../services/fakultet.service';

@Component({
  selector: 'app-prijava-ispita',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prijava-ispita.component.html',
  styleUrls: ['./prijava-ispita.component.css']
})
export class PrijavaIspitaComponent {
  ispitId: number | null = null;

  loading = false;
  successMsg = '';
  errorMsg = '';

  constructor(private fakultetService: FakultetService) {}

  prijavi() {
    this.reset();

    if (!this.ispitId || this.ispitId <= 0) {
      this.errorMsg = 'Unesi ispravan ID ispita.';
      return;
    }

    this.loading = true;
    this.fakultetService.prijaviIspit(this.ispitId).subscribe({
      next: (res) => {
        this.successMsg = `Uspješno prijavljen ispit (ID=${this.ispitId}).`;
        this.loading = false;
      },
      error: (err) => {
        this.errorMsg = this.extractError(err) || 'Greška pri prijavi.';
        this.loading = false;
      }
    });
  }

  otkazi() {
    this.reset();

    if (!this.ispitId || this.ispitId <= 0) {
      this.errorMsg = 'Unesi ispravan ID ispita.';
      return;
    }

    this.loading = true;
    this.fakultetService.otkaziPrijavu(this.ispitId).subscribe({
      next: () => {
        this.successMsg = `Prijava otkazana (ID=${this.ispitId}).`;
        this.loading = false;
      },
      error: (err) => {
        this.errorMsg = this.extractError(err) || 'Greška pri otkazivanju.';
        this.loading = false;
      }
    });
  }

  private reset() {
    this.successMsg = '';
    this.errorMsg = '';
  }

  private extractError(err: any): string | null {
    if (err?.error) {
      if (typeof err.error === 'string') return err.error;
      if (typeof err.error?.message === 'string') return err.error.message;
    }
    return null;
  }
}
