import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterModule} from '@angular/router';
import {PotvrdaService} from '../../services/potvrda.service';
import {Gradjanin, GradjaninDTO} from '../../model/gradjanin.model';
import {GradjaninService} from '../../services/gradjanin.service';


@Component({
  selector: 'app-profil',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {

  gradjanin?: Gradjanin;
  loading = true;
  gradjaninSaPotvrdom?: GradjaninDTO;




  constructor(private gradjaninService: GradjaninService, private potvrdaService: PotvrdaService, private router: Router) {

  }

  ngOnInit(): void {
    this.gradjaninService.getMe().subscribe({
      next: data => {
        this.gradjanin = data;
        this.loading = false;
      },
      error: err => {
        console.error(err);
        this.loading = false;
      }
    });

    this.gradjaninService.getProfilSaPotvrdom().subscribe({
      next: data => {
        this.gradjaninSaPotvrdom = data;
      },
      error: err => {
        console.error('Greška prilikom učitavanja potvrde:', err);
      }
    });
  }


  onEdit() {
    this.router.navigate(['/profil/uredi']);
  }

  onZatraziPotvrdu() {
    if (!this.gradjanin?.id) return;

    this.potvrdaService.zatraziPotvrdu(this.gradjanin.id)
      .subscribe({
        next: () => alert('Zahtev za potvrdu poslat'),
        error: err => alert('Došlo je do greške: ' + err.error?.message || err.message)
      });
  }

    preuzmiPotvrdu() {
      if (!this.gradjaninSaPotvrdom?.potvrdaId) {
        alert('Nemate potvrdu za preuzimanje');
        return;
      }

      this.potvrdaService.generisiPdfPotvrdu(this.gradjaninSaPotvrdom.potvrdaId)
        .subscribe({
          next: (blob: Blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'potvrda.pdf';
            a.click();
            window.URL.revokeObjectURL(url);
          },
          error: err => {
            console.error(err);
            alert('Greška prilikom preuzimanja PDF-a');
          }
        });
    }
  }
