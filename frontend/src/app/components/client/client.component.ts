import {Component, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  tab = 'list';

  // TODO - definir los headers de la tabla
  headers: string[] = ['Nombre', 'Apellido', 'Mail', 'Tipo de Usuario'];
  applications: any;

  constructor(private rs: HttpRequestsService) {
  }

  ngOnInit() {
    // TODO - definir de donde voy a obtener el id del cliente (podria estar en el LocalStorage al hacer el login)
    this.rs.getClientApplications(1).then(data => {
      this.applications = data;
    });
  }

}
