export interface Tab {
  name: string;
  component: any;
  props?: Record<string, any>; // optional!
}
