'use client';

import { usePathname } from 'next/navigation';
import { twMerge } from 'tailwind-merge';
import Link from 'next/link';

type Props = {
  menu: {
    title: string;
    value: string;
    path: string;
    onIcon: React.ReactNode;
    offIcon: React.ReactNode;
  };
};

export default function NavbarMenu({
  menu: { title, path, onIcon, offIcon },
}: Props) {
  const pathname = usePathname();

  const isActive = pathname === path;

  return (
    <Link
      href={path}
      className={twMerge(
        'flex w-fit flex-col items-center justify-center gap-[2px] bg-transparent',
      )}
    >
      {isActive ? onIcon : offIcon}
      <span className='text-xs'>{title}</span>
    </Link>
  );
}