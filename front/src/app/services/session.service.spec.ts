import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { is } from 'cypress/types/bluebird';

describe('SessionService', () => {
  let service: SessionService;
  const user: SessionInformation = {
    token: 'test',
    type: 'valide',
    id: 1,
    username: 'test',
    firstName: 'test',
    lastName: 'test',
    admin: false,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login and logout  a user', async () => {
    const result: boolean[] = [];
    service.$isLogged().subscribe((value) => result.push(value));
    service.logIn(user);
    expect(service.sessionInformation).toBe(user);
    service.logOut();
    expect(service.sessionInformation).toBeUndefined();
    expect(result).toEqual([false, true, false]);
  });

  it('should have correct session information', () => {
    expect(service.sessionInformation).toBeUndefined();
    service.logIn(user);
    expect(service.sessionInformation).toBe(user);
    service.logOut();
    expect(service.sessionInformation).toBeUndefined();
  });
});
