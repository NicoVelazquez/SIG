import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignInUpComponent} from './pages/sign-in-up/sign-in-up.component';
import {HomeComponent} from './pages/home/home.component';
import {AuthGuard} from './shared/auth.guard';
import {ReviewApplicationComponent} from './components/manager/review-application/review-application.component';
import {CreateNoteComponent} from './components/manager/create-note/create-note.component';


const routes: Routes = [
  {path: '', component: SignInUpComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'review-application/:id', component: ReviewApplicationComponent, canActivate: [AuthGuard]},
  {path: 'create-note/:id', component: CreateNoteComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
