import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-sign-in-up',
  templateUrl: './sign-in-up.component.html',
  styleUrls: ['./sign-in-up.component.scss']
})
export class SignInUpComponent implements OnInit {

  tab = 'signIn';

  constructor() {
  }

  ngOnInit() {
  }

  signIn() {
    //   this.tab = 'signIn';
    //   document.getElementById('sign-nav').children[1].classList.remove('uk-active');
    //   document.getElementById('sign-nav').children[0].classList.add('uk-active');
  }

  signUp() {
    //   this.tab = 'signUp';
    //   document.getElementById('sign-nav').children[0].classList.remove('uk-active');
    //   document.getElementById('sign-nav').children[1].classList.add('uk-active');
  }
}
