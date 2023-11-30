/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      spacing: {
        '1vw' : '1vw',
        '2vw' : '2vw',
        '3vw' : '3vw',
        '4vw' : '4vw',
        '5vw' : '5vw'
      }
    },
  },
  plugins: [
    function ({ addUtilities }) {
      const newUtilities = {};
      Array(100)
        .fill()
        .forEach((_, i) => {
          const number = i + 1;
          newUtilities[`.mt-${number}vw`] = { 'margin-top': `${number}vw` };
          newUtilities[`.mb-${number}vw`] = { 'margin-bottom': `${number}vw` };
          newUtilities[`.ml-${number}vw`] = { 'margin-left': `${number}vw` };
          newUtilities[`.mr-${number}vw`] = { 'margin-right': `${number}vw` };
          newUtilities[`.m-${number}vw`] = { 'margin': `${number}vw` };
          newUtilities[`.w-${number}vw`] = { width: `${number}vw` };
          newUtilities[`.h-${number}vh`] = { height: `${number}vh` };
        });
      addUtilities(newUtilities, ['responsive']);
    },
  ],
};
