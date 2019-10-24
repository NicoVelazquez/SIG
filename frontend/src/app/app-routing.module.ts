import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignInUpComponent} from './components/sign-in-up/sign-in-up.component';
import {HomeComponent} from './components/home/home.component';
import {AuthGuard, LoggedGuard} from './shared/guards.guard';
import {ReviewApplicationComponent} from './components/review-application/review-application.component';
import {CreateNoteComponent} from './components/manager/create-note/create-note.component';
import {NewApplicationComponent} from './components/client/new-application/new-application.component';
import {ReviewNoteComponent} from './components/review-note/review-note.component';


const routes: Routes = [
  {path: '', component: SignInUpComponent, canActivate: [LoggedGuard]},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'new-application', component: NewApplicationComponent, canActivate: [AuthGuard]},
  {path: 'review-application/:id', component: ReviewApplicationComponent, canActivate: [AuthGuard]},
  {path: 'create-note/:id', component: CreateNoteComponent, canActivate: [AuthGuard]},
  {path: 'review-note/:id', component: ReviewNoteComponent, canActivate: [AuthGuard]},
  {path: '**', component: HomeComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
