import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {SignInUpComponent} from './pages/sign-in-up/sign-in-up.component';
import {SignInComponent} from './pages/sign-in-up/sign-in/sign-in.component';
import {SignUpComponent} from './pages/sign-in-up/sign-up/sign-up.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ClientComponent } from './components/client/client.component';
import { ManagerComponent } from './components/manager/manager.component';
import { HomeComponent } from './pages/home/home.component';
import {NavbarComponent} from './components/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInUpComponent,
    SignInComponent,
    SignUpComponent,
    ClientComponent,
    ManagerComponent,
    HomeComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
