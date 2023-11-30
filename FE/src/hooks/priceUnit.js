export function priceUnit(price) {
  if (price < 2000) {
    return Math.ceil(price / 1) * 1;
  } else if (price < 5000) {
    return Math.ceil(price / 5) * 5;
  } else if (price < 20000) {
    return Math.ceil(price / 10) * 10;
  } else if (price < 50000) {
    return Math.ceil(price / 50) * 50;
  } else if (price < 200000) {
    return Math.ceil(price / 100) * 100;
  } else if (price < 500000) {
    return Math.ceil(price / 500) * 500;
  } else {
    return Math.ceil(price / 1000) * 1000;
  }
}