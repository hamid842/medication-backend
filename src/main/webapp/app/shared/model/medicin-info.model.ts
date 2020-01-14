import { Moment } from 'moment';

export interface IMedicinInfo {
  id?: number;
  name?: string;
  importantInfo?: string;
  usage?: string;
  initialCount?: string;
  promised?: string;
  refillInfo?: Moment;
  pharmacyNotes?: string;
}

export const defaultValue: Readonly<IMedicinInfo> = {};
