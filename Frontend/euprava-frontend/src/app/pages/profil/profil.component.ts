import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Gradjanin, GradjaninService} from '../../services/gradjanin.service';
import {Router, RouterModule} from '@angular/router';
import {PotvrdaService} from '../../services/potvrda.service';


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
}
