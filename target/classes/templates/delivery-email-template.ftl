<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/style.css" />
    <title>Document</title>
    <style>
      * {
        padding: 0;
        margin: 0;
        box-sizing: border-box;
      }

      .sec-33 {
        /* padding: 10px 0px; */
        padding: 0px 0px;
      }

      .container {
        width: 1140px;
        margin: 0 auto;
      }

      .row {
        display: flex;
        flex-wrap: wrap;
      }

      .col-4 {
        width: 33.33%;
        padding: 0px 15px;
      }

      .bg-color {
        background-color: #36bc9b;
        text-align: center;
        color: #fff;
        padding: 15px 0px;
      }

      .been-sat {
        background-color: #003e33;
        color: #ffff;
      }

      .ibrahim li {
        list-style: none;
      }

      .been-sat {
        padding: 10px;
      }

      .font-weight-bold {
        font-weight: bold;
      }

      .reuest,
      .receive-your {
        padding: 10px 0px;
        line-height: 25px;
      }

      .are {
        padding: 15px 0px;
        display: block;
      }

      .ibrahim {
        padding: 5px 10px;
        line-height: 25px;
      }

      .asdc {
        display: block;
        font-size: 20px;
      }
    </style>
  </head>

  <body>
    <div class="sec-33" style="font-family: Calibri">
      <div class="row">
        <div class="col-12 col-md-6 col-xl-4">
          <div class="border-r">
            <div class="bg-color py-3">
              <span class="asdc text-white text-center"
                >Upcoming orders to be delivered.</span
              >
              <span class="asdc text-white text-center"
                >Delivery Date: <span class="font-weight-bold">${deliveryDate}</span>
              </span>
            </div>

                <span class="d-block are pb-3"
                  >Below are the order numbers :</span
                >
              </div>

              <ul class="ibrahim">
                 <li><strong>Delivery Date :</strong>${edate}</li>
                <li><strong>Ordered No :</strong> ${items}</li>
              </ul
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>