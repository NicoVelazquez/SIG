import {Component, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  headers: string[] = ['Id', 'Dia', 'Estado', 'Ver'];
  applications = [];

  constructor(private rs: HttpRequestsService, private router: Router) {
  }

  ngOnInit() {
    // TODO - definir de donde voy a obtener el id del cliente (podria estar en el LocalStorage al hacer el login)
    this.rs.getClientApplications().then(data => {
      this.applications = data;
    });
  }

  reviewApplication(application: any) {
    if (application.state.toLowerCase() === 'nueva') {
      this.router.navigate(['/review-application/' + application.id], {queryParams: {state: 'nueva', user: 'client'}});
    } else {
      this.router.navigate(['/review-application/' + application.id]);
    }
  }

  reviewNote(application: any) {
    this.router.navigate(['/review-note/' + application.id], {queryParams: {user: 'client'}});
  }
}
