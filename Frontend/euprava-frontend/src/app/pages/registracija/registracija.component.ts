import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registracija',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './registracija.component.html',
  styleUrls: ['./registracija.component.css']
})
export class RegistracijaComponent {

  registracijaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) {
    this.registracijaForm = this.fb.group({
      ime: ['', Validators.required],
      prezime: ['', Validators.required],
      korisnickoIme: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      lozinka: ['', Validators.required],
      uloga: ['ROLE_STUDENT'] // default
    });

  }

  submit() {
    if (this.registracijaForm.invalid) return;

    this.authService.register(this.registracijaForm.value)
      .subscribe({
        next: (res: any) => console.log('Registracija uspešna', res),
        error: (err: any) => console.error('Greška', err)
      });

  }
}
