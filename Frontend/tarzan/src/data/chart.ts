export interface ChartColorOption {
  backgroundColor: string;
  borderColor: string;
  pointBackgroundColor: string;
  pointBorderColor: string;
  pointHoverBackgroundColor: string;
  pointHoverBorderColor: string;
}

export interface ChartDataOption {
  label: string;
  data: number[];
}

export const ChartColorOptionDefault: ChartColorOption[] = [
  {
    backgroundColor: "rgba(127, 201, 127, 0.25)",
    borderColor: "rgba(127, 201, 127, 1)",
    pointBackgroundColor: "rgba(127, 201, 127, 1)",
    pointBorderColor: "#fff",
    pointHoverBackgroundColor: "#fff",
    pointHoverBorderColor: "rgba(127, 201, 127, 1)",
  },
  {
    backgroundColor: "rgba(255,99,132,0.2)",
    borderColor: "rgba(255,99,132,1)",
    pointBackgroundColor: "rgba(255,99,132,1)",
    pointBorderColor: "#fff",
    pointHoverBackgroundColor: "#fff",
    pointHoverBorderColor: "rgba(255,99,132,1)",
  },
  {
    backgroundColor: "rgba(190, 174, 212,0.2)",
    borderColor: "rgba(190, 174, 212,1)",
    pointBackgroundColor: "rgba(190, 174, 212,1)",
    pointBorderColor: "#fff",
    pointHoverBackgroundColor: "#fff",
    pointHoverBorderColor: "rgba(190, 174, 212,1)",
  },
];
