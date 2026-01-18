import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Oglas } from '../../model/oglas.model';

@Component({
  selector: 'app-oglas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './oglas.component.html',
  styleUrls: ['./oglas.component.css']
})
export class OglasComponent {
  @Input() oglas!: Oglas;
}
