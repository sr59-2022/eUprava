import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Oglas } from '../../model/oglas.model';
import {OglasService} from '../../services/oglas.service';

@Component({
  selector: 'app-oglas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './oglas.component.html',
  styleUrls: ['./oglas.component.css']
})
export class OglasComponent {
  @Input() oglas!: Oglas;

  successMsg: string | null = null;
  errorMsg: string | null = null;

  constructor(private oglasService: OglasService) {
  }

  prijaviSe() {
    this.successMsg = null;
    this.errorMsg = null;

    this.oglasService.prijaviNaOglas(this.oglas.idOglasa).subscribe({
      next: () => {
        this.successMsg = 'Uspešno ste se prijavili na oglas.';
      },
      error: (err) => {
        if (err.status === 400) {
          alert('Već ste prijavljeni na ovaj oglas.');
        } else {
          alert('Greška prilikom prijave.');
        }
      }
    });
  }
}
