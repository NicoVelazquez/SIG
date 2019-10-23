import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';
import {NewApplicationComponent} from './new-application/new-application.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  tab = 'list';

  // TODO - definir los headers de la tabla
  headers: string[] = ['Id', 'Dia', 'Estado', 'Ver'];
  applications = [];

  @ViewChild(NewApplicationComponent, {static: false}) newApplication: NewApplicationComponent;

  constructor(private rs: HttpRequestsService, private router: Router) {
  }

  ngOnInit() {
    // TODO - definir de donde voy a obtener el id del cliente (podria estar en el LocalStorage al hacer el login)
    this.rs.getClientApplications().then(data => {
      this.applications = data;
    });
  }

  validApplication(): boolean {
    if (this.newApplication) {
      return this.newApplication.isValidApplication();
    }
  }

  async generateApplication() {
    const newApplication = await this.newApplication.createApplication();
    console.log(newApplication);
    this.applications.push(newApplication);
    this.tab = 'list';
  }

  reviewApplication(application: any) {
    this.router.navigate(['/review-application/' + application.id]);
  }

}
