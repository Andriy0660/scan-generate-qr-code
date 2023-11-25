# scan-generate-qr-code


## API Reference

### Create QR Code

  `POST /create/{text}`

### Accepts

| Parameter | Type                 |
| :-------- | :------------------- |
| `info`    | `String`             |

### Get QR Code


  `GET /{projectId}/qrcode`

### Accepts

| Parameter | Type                 |
| :-------- | :------------------- |
| `projectId` | `Integer` |

### Returns
Returns a png of QR Code



### Scan QR Code


  `GET /scan`

### Returns
Returns a info from QR Code
