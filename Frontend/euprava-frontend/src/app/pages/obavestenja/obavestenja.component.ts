import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ObavestenjeDTO} from '../../model/obavestenje.model';
import {ObavestenjeService} from '../../services/obavestenje.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-obavestenja',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './obavestenja.component.html',
  styleUrl: './obavestenja.component.css'
})
export class ObavestenjaComponent implements OnInit {
  @Input() obavestenja: ObavestenjeDTO[] = [];
  @Input() brojNeprocitanih: number = 0;
  @Output() close = new EventEmitter<void>();
  @Output() procitano = new EventEmitter<number>();


  constructor(private obavestenjeService: ObavestenjeService) {}

  ngOnInit(): void {}


  loadObavestenja(): void {
    this.obavestenjeService.getObavestenja().subscribe((data) => {
      this.obavestenja = data;
    });
  }

  loadBrojNeprocitanih(): void {
    this.obavestenjeService.getBrojNeprocitanih().subscribe((count) => {
      this.brojNeprocitanih = count;
    });
  }

  oznaciProcitano(id: number): void {
    this.obavestenjeService.oznaciProcitano(id).subscribe(() => {
      this.obavestenja = this.obavestenja.filter(o => o.id !== id);
      if (this.brojNeprocitanih > 0) this.brojNeprocitanih--;
    });
  }


  closeModal() {
    this.close.emit();
  }
}
