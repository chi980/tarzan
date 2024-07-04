export interface Check {
  idx: number;
  name: string;
  value: boolean;
}

export interface CheckList {
  idx: number;
  title: string;
  contents: Check[];
  isRotated: boolean;
  canSee: boolean;
}
