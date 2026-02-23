import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

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
    private authService: AuthService,
    private router: Router
  ) {
    this.registracijaForm = this.fb.group({
      ime: ['', Validators.required],
      prezime: ['', Validators.required],
      korisnickoIme: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      lozinka: ['', Validators.required],
      uloge: this.fb.control<string[]>([], Validators.required)
    });

  }

  onRoleChange(event: any) {
    const roles = this.registracijaForm.get('uloge')?.value as string[];

    if (event.target.checked) {
      this.registracijaForm.get('uloge')?.setValue([...roles, event.target.value]);
    } else {
      this.registracijaForm.get('uloge')?.setValue(
        roles.filter(r => r !== event.target.value)
      );
    }
  }

  submit() {
    if (this.registracijaForm.invalid) return;

    this.authService.register(this.registracijaForm.value)
      .subscribe({
        next: (res: any) => {
          console.log('Registracija uspešna', res);
          alert('Uspešna registracija! Sada se možete prijaviti.');
          this.router.navigate(['/login']);
        },
        error: (err: any) => {
          console.error('Greška', err);
          alert('Došlo je do greške prilikom registracije.');
        }
      });

  }
}
