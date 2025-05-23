import { ForwardedRef, forwardRef } from 'react';
import { twMerge } from 'tailwind-merge';

type Props = Omit<React.ComponentPropsWithRef<'input'>, 'type'> & {
  type: 'text' | 'password' | 'email' | 'search';
};

const Input = forwardRef(
  (props: Props, ref: ForwardedRef<HTMLInputElement>) => {
    const { className, ...rest } = props;
    return (
      <input
        ref={ref}
        className={twMerge(
          'h-11 w-full rounded-sm px-2 font-medium focus:outline-none bg-white text-black transition-colors duration-300',
          className,
        )}
        {...rest}
      />
    );
  },
);

Input.displayName = 'Input';
export default Input;