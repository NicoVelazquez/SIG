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

  currentUserRole(): string {
    if (JSON.parse(localStorage.getItem('user'))) {
      return (JSON.parse(localStorage.getItem('user'))).role;
    }
  }

  signInWithEmailAndPassword(mail: string, password: string): Promise<any> {
    const user = {email: mail, role: 'client'};
    localStorage.setItem('user', JSON.stringify(user));
    return Promise.resolve(true);
  }

  // createUserWithEmailAndPassword(username: string, password: string): Promise<any> {
  //   return Promise.resolve(false);
  // }
}
