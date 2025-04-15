import Logo from '@/components/layout/Logo';

export default function DefaultHeader() {
  return (
    <header className="flex w-full items-center justify-between border-b rounded-2xl border bg-white px-4 py-[15px]">
      <Logo/>
      <div/>
    </header>
  );
}