// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.

export const environment = {
  production: false,
  paymentApiUrl: 'https://domine-app-test.chargebeeportal.com',
  baseUrl: 'http://localhost:8080/',
  uploadImageUrl: 'http://localhost:5984/images/',
  sizeLimit: 2048 * 1000,
  googleKey: '6LfH0RgUAAAAABvJZsrsGTeeMajb44qKxG4qmwWe',
  appTitle: 'Dominapp'
};
