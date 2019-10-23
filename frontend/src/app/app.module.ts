import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {SignInUpComponent} from './components/sign-in-up/sign-in-up.component';
import {SignInComponent} from './components/sign-in-up/sign-in/sign-in.component';
import {SignUpComponent} from './components/sign-in-up/sign-up/sign-up.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { ClientComponent } from './components/client/client.component';
import { ManagerComponent } from './components/manager/manager.component';
import { HomeComponent } from './components/home/home.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import { ReviewApplicationComponent } from './components/review-application/review-application.component';
import { NewApplicationComponent } from './components/client/new-application/new-application.component';
import { CreateNoteComponent } from './components/manager/create-note/create-note.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInUpComponent,
    SignInComponent,
    SignUpComponent,
    ClientComponent,
    ManagerComponent,
    HomeComponent,
    NavbarComponent,
    ReviewApplicationComponent,
    NewApplicationComponent,
    CreateNoteComponent
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
