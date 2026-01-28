import {Component, EventEmitter, Output} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { OglasService } from '../../services/oglas.service';
import { OglasRequest } from '../../model/oglas-request.model';
import {TipOglasa} from '../../model/oglas.model';

@Component({
  selector: 'app-dodaj-oglas',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './dodaj-oglas.component.html',
  styleUrls: ['./dodaj-oglas.component.css']
})
export class DodajOglasComponent {

  oglas: OglasRequest = {
    nazivPozicije: '',
    opis: '',
    nazivKompanije: '',
    rokPrijave: '',
    tipOglasa: TipOglasa.POSAO,
    oblast: ''
  };

  constructor(private oglasService: OglasService) {}
  @Output() oglasDodat = new EventEmitter<void>();
  save() {
    this.oglasService.dodajOglas(this.oglas).subscribe({
      next: () => {
        alert('Oglas dodat!');
        this.oglasDodat.emit();
      },
      error: err => console.error(err)
    });
  }

}
