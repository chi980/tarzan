declare namespace kakao {
    namespace maps {
      class Map {
        constructor(container: HTMLElement, options: any);
      }
  
      class LatLng {
        constructor(latitude: number, longitude: number);
      }
  
      class Marker {
        constructor(options: { position: LatLng });
      }
  
      class MarkerClusterer {
        constructor(options: { map: Map; averageCenter: boolean; minLevel: number });
        clear(): void;
        addMarkers(markers: Marker[]): void;
      }
  
      namespace event {
        function addListener(target: any, event: string, callback: (e: any) => void): void;
      }
  
      function load(callback: () => void): void;
  
      namespace services {
        class Geocoder {
          coord2Address(
            lng: number,
            lat: number,
            callback: (result: any, status: services.Status) => void
          ): void;
        }
  
        enum Status {
          OK = "OK",
        }
      }
    }
  }
  