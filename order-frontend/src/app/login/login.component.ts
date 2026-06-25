import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
    username: string = '';
    password: string = '';
    message: string = '';

    constructor(private authService: AuthService){ }

    login(){
      this.authService.login(this.username, this.password).subscribe({
        next: (response: any) => {
          this.message = 'Login successful! Token received.';
          localStorage.setItem('token', response.token);
          console.log('Token:', response.token);
        },
        error: (err) => {
          this.message = 'Login failed: ' + err.message;
        }
    });
  }
}
