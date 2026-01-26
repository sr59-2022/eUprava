import { Component, OnInit } from '@angular/core';
import { PotvrdaService, GradjaninDTO } from '../../services/potvrda.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-potvrde',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-potvrde.component.html',
  styleUrls: ['./admin-potvrde.component.css']
})
export class AdminPotvrdeComponent implements OnInit {
  gradjani: GradjaninDTO[] = [];
  loading = false;

  constructor(private potvrdaService: PotvrdaService) {}

  ngOnInit() {
    this.loadGradjani();
  }

  loadGradjani() {
    this.loading = true;
    this.potvrdaService.getGradjani().subscribe({
      next: (data: GradjaninDTO[]) => {
        this.gradjani = data;
        this.loading = false;
      },
      error: (err: any) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  izdajPotvrdu(gradjaninId: number) {
    this.potvrdaService.izdajPotvrdu(gradjaninId)
      .subscribe({
        next: () => {
          alert('Potvrda izdata!');
          this.loadGradjani();
        },
        error: (err: any) => alert(err.error?.message || 'Greška prilikom izdavanja')
      });
  }
}
