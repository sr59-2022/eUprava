import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FakultetService, UverenjeDto } from '../../services/fakultet.service';
imports: [CommonModule, FormsModule];
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-uverenja',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './uverenja.component.html',
  styleUrl: './uverenja.component.css'
})
export class UverenjaComponent implements OnInit {
  tipovi: string[] = ['STUDIRANJE', 'ZA_DOM', 'ZA_STIPENDIJU', 'ZA_PREVOZ', 'ZA_BANKE'];
  selectedTip = 'STUDIRANJE';

  uverenja: UverenjeDto[] = [];
  loading = false;
  issuing = false;
  error: string | null = null;

  constructor(private fakultetService: FakultetService) {}

  ngOnInit(): void {
    this.ucitajUverenja();
  }

  ucitajUverenja() {
    this.loading = true;
    this.error = null;

    this.fakultetService.mojaUverenja().subscribe({
      next: (data) => {
        this.uverenja = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri učitavanju uverenja.';
        this.loading = false;
      }
    });
  }

  izdaj() {
    this.issuing = true;
    this.error = null;

    this.fakultetService.izdajUverenje(this.selectedTip).subscribe({
      next: () => {
        this.issuing = false;
        this.ucitajUverenja(); // osveži listu
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri izdavanju uverenja.';
        this.issuing = false;
      }
    });
  }

  preuzmiPdf(u: UverenjeDto) {
    this.error = null;

    this.fakultetService.preuzmiUverenjePdf(u.id).subscribe({
      next: (blob) => {
        const filename = `uverenje-${u.id}.pdf`;
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = url;
        a.download = filename;
        a.click();

        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        this.error = err?.error?.message || 'Greška pri preuzimanju PDF-a.';
      }
    });
  }
}
