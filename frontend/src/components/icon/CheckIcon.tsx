import Image from 'next/image';
import checkIcon from '../../../public/check.svg'
import { twMerge } from 'tailwind-merge';

export default function CheckIcon({ className }: { className?: string }) {
  return (
    <Image
      src={checkIcon}
      alt="check"
      className={twMerge('h-0.5 w-0.5', className)}
    />
  );
}
