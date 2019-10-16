import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignInUpComponent} from './pages/sign-in-up/sign-in-up.component';
import {HomeComponent} from './pages/home/home.component';
import {AuthGuard} from './shared/auth.guard';


const routes: Routes = [
  {path: '', component: SignInUpComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
