import {Component, OnInit} from '@angular/core';
import {HttpRequestsService} from '../../shared/services/http-requests.service';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css']
})
export class ManagerComponent implements OnInit {

  tab = 'list';

  // TODO - definir los headers de la tabla
  headers: string[] = ['Nombre', 'Apellido', 'Mail', 'Tipo de Usuario'];
  applications = [];
  filteredApplications = [];

  client: string;

  constructor(private rs: HttpRequestsService) {
  }

  ngOnInit() {
    // TODO - definir de donde voy a obtener el id del cliente (podria estar en el LocalStorage al hacer el login)
    this.rs.getClientApplications(1).then(data => {
      this.applications = data;
      this.filteredApplications = data;
    });
  }

  filterApplications() {
    this.filteredApplications = this.applications.filter(e => e.name.includes(this.client));
  }
}
