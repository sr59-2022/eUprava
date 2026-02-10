import {Component, OnInit} from '@angular/core';
import {SluzbaService} from '../../services/sluzba.service';
import { HttpClientModule } from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'app-sluzba-diplomirani',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule],
  templateUrl: './sluzba-diplomirani.component.html',
  styleUrl: './sluzba-diplomirani.component.css'
})
export class SluzbaDiplomiraniComponent implements OnInit {

  studenti: any[] = [];

  constructor(private sluzbaService: SluzbaService) {}

  ngOnInit() {
    this.sluzbaService.getKandidati().subscribe({
      next: res => this.studenti = res,
      error: () => alert('Greška pri učitavanju diplomiranih studenata')
    });
  }
}
