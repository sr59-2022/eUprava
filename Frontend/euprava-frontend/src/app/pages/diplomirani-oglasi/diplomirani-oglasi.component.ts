import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Oglas } from '../../model/oglas.model';
import { OglasService } from '../../services/oglas.service';
import { OglasComponent } from '../oglas/oglas.component';

@Component({
  selector: 'app-diplomirani-oglasi',
  standalone: true,
  imports: [CommonModule, OglasComponent],
  templateUrl: './diplomirani-oglasi.component.html',
  styleUrls: ['./diplomirani-oglasi.component.css']
})
export class DiplomiraniOglasiComponent implements OnInit {

  oglasi: Oglas[] = [];
  loading = true;
  errorMsg: string | null = null;

  constructor(private oglasService: OglasService) {}

  ngOnInit(): void {
    this.oglasService.getOglasiZaDiplomirane().subscribe({
      next: (data) => {
        this.oglasi = data ?? [];
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Oglasi su dostupni samo studentima koji su diplomirali.';
        this.loading = false;
      }
    });
  }
}
