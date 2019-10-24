import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) {
  }

  public isLoggedIn() {
    return JSON.parse(localStorage.getItem('user'));
  }

  public signOut() {
    localStorage.removeItem('user');
    this.router.navigate(['']);
  }

  public isClient() {
    return this.currentUserRole() ? this.currentUserRole().toLowerCase() === 'client' : false;
  }

  public isManager() {
    return this.currentUserRole() ? this.currentUserRole().toLowerCase() === 'manager' : false;
  }

  currentUserRole(): string {
    if (JSON.parse(localStorage.getItem('user'))) {
      return (JSON.parse(localStorage.getItem('user'))).role;
    }
  }
}
