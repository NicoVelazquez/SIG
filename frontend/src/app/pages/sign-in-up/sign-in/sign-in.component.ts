import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../shared/services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  public signInForm: FormGroup;
  public showPass: boolean;

  invalidEmailOrPassword = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.signInForm = fb.group({
      email: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
    });
  }

  ngOnInit() {
    this.showPass = false;

    this.signInForm.get('email').valueChanges.subscribe(() => {
      this.invalidEmailOrPassword = false;
    });

    this.signInForm.get('password').valueChanges.subscribe(() => {
      this.invalidEmailOrPassword = false;
    });
  }

  showPassword() {
    this.showPass = !this.showPass;
  }

  signIn() {
    this.authService.signInWithEmailAndPassword(this.signInForm.value.email, this.signInForm.value.password).then(() => {
      this.router.navigate(['/home']).then(() => {
        this.signInForm.reset();
      });
    }).catch(err => {
      if (err.code === 'auth/wrong-password') {
        this.invalidEmailOrPassword = true;
      } else {
        console.log(err);
      }
    });
  }
}
